#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
DB_USER="${GUVAULT_DB_USER:-root}"
DB_HOST="${GUVAULT_DB_HOST:-localhost}"

printf 'Creating GU-Vault schema...\n'
mysql -h "$DB_HOST" -u "$DB_USER" -p < "$ROOT_DIR/database/schema.sql"
printf 'Loading seeded demo data...\n'
mysql -h "$DB_HOST" -u "$DB_USER" -p gu_vault < "$ROOT_DIR/database/seed.sql"
printf 'Database setup complete.\n'
