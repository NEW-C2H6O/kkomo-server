import http from 'k6/http';
import { sleep, group } from 'k6';
import { baseUrl } from './env.js';

export default function () {
    group('OTT 예약 동시 요청 시 최초 요청을 제외한 다른 요청은 모두 실패', function () {
        const url = baseUrl + '/reservations';
        const body = JSON.stringify({
            ottId: 1,
            profileId: 1,
            time: {
                start: '2024-12-29T01:00:00',
                end: '2024-12-29T02:00:00'
            }
        });

        console.log('Request body: ', body);

        const headers = { 'Content-Type': 'application/json' };

        const responses = http.batch([
            ['POST', url, body, { headers: headers }],
            ['POST', url, body, { headers: headers }],
            ['POST', url, body, { headers: headers }],
        ]);

        responses.forEach((item, index) => {
            console.log(`Response ${index + 1}: `, item.body);

        });

        sleep(1);
    });
}
