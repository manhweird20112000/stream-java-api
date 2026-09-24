# Google OAuth Login Design

## Goal

Implement Google OAuth login for the Stream auth service so a frontend can send users to Google, receive a service-issued JWT after successful login, and call a protected `GET /api/auth/me` endpoint with that JWT.

## Scope

This first version implements OAuth client login only. The auth service will not act as an OAuth2 authorization server, will not persist users, and will not issue refresh tokens.

Included:

- Google OAuth2 login through Spring Security.
- JWT access token issued by this auth service after successful Google login.
- Redirect to the frontend success URL with the token.
- Bearer-token authentication for protected API endpoints.
- `GET /api/auth/me` endpoint returning authenticated user details from the JWT.
- Tests for security configuration, token creation/validation, success redirect behavior, and `/api/auth/me`.

Not included:

- User database tables or repository.
- Account linking.
- Refresh tokens.
- Logout token revocation.
- Multiple OAuth providers.
- Frontend implementation.

## Configuration

Add the following environment-backed configuration:

- `GOOGLE_CLIENT_ID`: Google OAuth client id.
- `GOOGLE_CLIENT_SECRET`: Google OAuth client secret.
- `OAUTH_SUCCESS_REDIRECT_URI`: frontend callback URL, default `http://localhost:3000/auth/callback`.
- `JWT_SECRET`: HMAC secret used to sign Stream access tokens.
- `JWT_ISSUER`: token issuer, default `stream-auth-service`.
- `JWT_EXPIRATION_SECONDS`: access token lifetime, default `3600`.

The local OAuth redirect URI registered in Google Cloud should be:

```text
http://localhost:8080/login/oauth2/code/google
```

## Architecture

Use Spring Security's OAuth2 client support for the Google authorization code flow. Keep OAuth wiring in infrastructure/config classes and keep token creation in a small application service so it can be tested without a web container.

Primary units:

- `SecurityConfig`: configures public endpoints, OAuth2 login, JWT bearer authentication, and stateless API behavior.
- `JwtProperties`: binds JWT config.
- `OAuthProperties`: binds frontend redirect config.
- `JwtTokenService`: creates and validates Stream JWT access tokens.
- `OAuth2LoginSuccessHandler`: converts the authenticated Google principal into a Stream JWT and redirects to the frontend callback.
- `AuthenticatedUser`: application DTO for the current user.
- `CurrentUserController`: exposes `GET /api/auth/me`.

## Request Flow

Login starts when the frontend navigates to:

```http
GET /oauth2/authorization/google
```

Spring Security redirects the browser to Google. After Google authenticates the user, it redirects back to:

```http
GET /login/oauth2/code/google
```

On success, `OAuth2LoginSuccessHandler` reads these Google attributes:

- `sub` as the external Google subject.
- `email` as the user email.
- `name` as the display name.
- `picture` as the avatar URL.

It signs a Stream JWT containing those values and redirects to:

```text
{OAUTH_SUCCESS_REDIRECT_URI}?token={jwt}
```

The frontend uses that token as:

```http
Authorization: Bearer {jwt}
```

## API Behavior

Public endpoints:

- `GET /api/auth/health`
- `GET /oauth2/authorization/google`
- `GET /login/oauth2/code/google`

Protected endpoint:

```http
GET /api/auth/me
Authorization: Bearer {jwt}
```

Successful response data:

```json
{
  "subject": "google-subject",
  "email": "user@example.com",
  "name": "User Name",
  "picture": "https://example.com/avatar.png"
}
```

The existing `ApiResponseBodyAdvice` should wrap this DTO in the standard API response envelope.

## Error Handling

Invalid or missing JWTs on protected endpoints return `401 Unauthorized`.

OAuth provider failures use Spring Security's default OAuth failure behavior for this version. A custom failure redirect can be added later when the frontend contract needs it.

## Testing

Add focused tests:

- `JwtTokenServiceTests` verifies token generation, parsing, claims, and invalid token rejection.
- `OAuth2LoginSuccessHandlerTests` verifies the redirect URL includes a signed token and preserves the configured frontend callback URL.
- `SecurityConfigTests` verifies health and OAuth login entrypoints are public while `/api/auth/me` requires authentication.
- `CurrentUserControllerTests` verifies a valid JWT returns the authenticated user DTO through `/api/auth/me`.

Run the full suite with:

```bash
mvn test
```

## Implementation Constraints

- Keep the first version minimal and dependency-light.
- Use Spring Security and its OAuth2 client support instead of hand-rolling the Google token exchange.
- Use one JWT library through Spring Security's OAuth2 JOSE support instead of a separate JWT dependency.
- Do not introduce persistence until a user profile lifecycle is explicitly required.
- Preserve the existing package boundaries described in `README.md`.
