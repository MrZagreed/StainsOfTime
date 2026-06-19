# Architecture draft

## Goal

Open-source Android rewrite focused on tethering, proxying, and session tracking.

## Planned modules

- app: Android entry point and UI shell.
- core: mode models, config, and session state.
- service: background orchestration.
- proxy: local HTTP, HTTPS, and SOCKS server.
- transport: USB, Bluetooth, and Wi-Fi adapters.

## Milestones

1. Buildable Android skeleton.
2. Settings screen and service controls.
3. Session registry and traffic counters.
4. Proxy protocol handlers.
5. Transport abstraction layer.
