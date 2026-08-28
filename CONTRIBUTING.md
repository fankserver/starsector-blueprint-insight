# Contributing

Thank you for contributing to Blueprint Insight.

## Before opening an issue

- Search existing issues first.
- Confirm the problem still occurs with the latest release.
- Include the Starsector version and whether Industrial.Evolution is enabled.
- Test without unrelated mods where practical.
- For crashes or loading errors, attach the relevant section of `starsector.log`.

Do not upload save files, logs, or screenshots containing personal information or credentials.

## Development

No host Java installation is required. Docker builds against the API jars from a local Starsector installation:

```bash
./scripts/build-docker.sh
```

GitHub release builds use compile-only signature stubs:

```bash
./scripts/build-ci-docker.sh
./scripts/build-release.sh v0.1.0
```

Before submitting a pull request:

1. Build against the actual Starsector API with `build-docker.sh`.
2. Build the CI version with `build-ci-docker.sh`.
3. Run a clean in-game smoke test.
4. Test both with and without Industrial.Evolution enabled.
5. Do not commit Starsector or other mods' proprietary/runtime dependency jars.

## Pull requests

Keep changes focused and explain gameplay effects, compatibility concerns, and validation performed. By contributing, you agree that your contribution is licensed under the repository's MIT License.
