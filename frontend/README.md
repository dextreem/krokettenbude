# Croqueteria UI

## Get API types

```bash
curl http://localhost:8080/v3/api-docs -o api-docs.json
npx openapi-typescript ./api-docs.json --output ./src/api/api-types.ts

openapi-generator generate -i ./api-docs.json -g typescript-fetch -o ./src/api-client
# or
podman run --rm -v ${PWD}:/local docker.io/openapitools/openapi-generator-cli generate \
  -i /local/api-docs.json \
  -g typescript-fetch \
  -o /local/src/api-client
```
