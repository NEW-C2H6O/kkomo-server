package kkomo.global.support;

import org.springframework.data.domain.Slice;

/**
 * slice에서 마지막 원소의 id를 추출해요. 만약 slice가 비어있다면 null을 반환해요.
 */
@FunctionalInterface
public interface LastIdExtractor<T> {

    Long extract(Slice<T> slice);
}
