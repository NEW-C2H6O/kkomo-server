package kkomo.auth.domain;

public record SignUpCommand(
    String name,
    String profileImage,
    String email,
    String provider
) {
}
