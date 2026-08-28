# Blueprint Insight

A save-safe Starsector 0.98a quality-of-life mod that prepends blueprint ownership to the ship summary shown in fleet, storage, market, and refit tooltips.

## Tooltip states

Without Industrial.Evolution:

- **Blueprint: known** — your faction can produce the hull.
- **Blueprint: unknown** — your faction cannot currently produce the hull.

With Industrial.Evolution enabled:

- **Blueprint: known**
- **Reverse engineering: N%**
- **Blueprint: unknown** when there is no research progress.

Industrial.Evolution is optional. Blueprint Insight detects its `IndEvo` mod ID at runtime and reads its authoritative Engineering Hub progress map without linking against IndEvo classes.

D-hulls and restored hulls are normalized to the same base hull used by Industrial.Evolution and vanilla blueprint ownership checks.

## Requirements

- Starsector 0.98a

There are no required library mods.

## Installation

1. Download the release ZIP.
2. Extract it into the Starsector `mods` directory.
3. Enable **Blueprint Insight** in the launcher.
4. Fully restart Starsector when adding or removing it; ship descriptions are loaded during application startup.

## Save-game safety

Blueprint Insight is safe to add to or remove from an existing campaign. It stores no campaign data and does not modify individual fleet members or variants. Hull-specification description prefixes are annotated only in memory and refreshed by a transient campaign script. This targets the cached summary used by compact ship tooltips without modifying Codex description records.

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
