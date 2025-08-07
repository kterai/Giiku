import { execSync } from 'child_process';
import * as path from 'path';
import * as dotenv from 'dotenv';

export default async function globalSetup() {
  dotenv.config();
  const host = process.env.DB_HOST || 'localhost';
  const port = process.env.DB_PORT || '5432';
  const user = process.env.DB_USER || 'postgres';
  const password = process.env.DB_PASSWORD || 'postgres';
  const db = process.env.DB_NAME || 'unryu_db_autotest';

  const sqlFile = path.resolve(__dirname, '../src/test/resources/db/testdata/autotest_sample_data.sql');
  const cmd = `psql -h ${host} -p ${port} -U ${user} -d ${db} -f "${sqlFile}"`;
  execSync(cmd, {
    stdio: 'inherit',
    env: {
      ...process.env,
      PGPASSWORD: password,
      PGCLIENTENCODING: 'UTF8',
    },
  });
}
