import type { ConfigFile } from '@rtk-query/codegen-openapi'

const config: ConfigFile = {
  schemaFile: 'http://localhost:8080/v3/api-docs',
  apiFile: './lib/api.ts',
  apiImport: 'api',
  outputFile: './lib/generated-api.ts',
  exportName: 'clientinApi',
  hooks: true,
}

export default config
