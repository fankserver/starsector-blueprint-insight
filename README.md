# Blueprint Insight

A save-safe Starsector 0.98a quality-of-life mod that displays blueprint ownership directly in ship information tooltips.

## Tooltip states

Without Industrial.Evolution:

- **Blueprint known** — your faction can produce the hull.
- **Blueprint unknown** — your faction cannot currently produce the hull.

With Industrial.Evolution enabled:

- **Blueprint known**
- **Blueprint unknown — reverse engineering: N%**
- **Blueprint unknown — no reverse-engineering progress**

Industrial.Evolution is optional. Blueprint Insight detects its `IndEvo` mod ID at runtime and reads its authoritative Engineering Hub progress map without linking against IndEvo classes.

D-hulls and restored hulls are normalized to the same base hull used by Industrial.Evolution and vanilla blueprint ownership checks.

## Requirements

- Starsector 0.98a

There are no required library mods.

## Installation

1. Download the release ZIP.
2. Extract it into the Starsector `mods` directory.
3. Enable **Blueprint Insight** in the launcher.
4. Fully restart Starsector when adding or removing it; hull specifications are annotated during application startup.

## Save-game safety

Blueprint Insight is safe to add to or remove from an existing campaign. It stores no campaign data and does not modify individual fleet members or variants. Its zero-effect status hullmod is added only to runtime hull specifications, which Starsector rebuilds from mod data on every application start.

A full restart is required after enabling or disabling the mod.

## Building without host Java

Compile against a local Starsector installation using an ephemeral Docker JDK:

```bash
./scripts/build-docker.sh
```

CI uses signature-only API stubs because the proprietary Starsector API JAR cannot be redistributed:

```bash
./scripts/build-ci-docker.sh
```

Build an install-ready release archive:

```bash
./scripts/build-release.sh v0.1.0
```

## License

[MIT](LICENSE)
