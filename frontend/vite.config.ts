import { defineConfig } from "vite";
import react from "@vitejs/plugin-react-swc";

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      "/api/v1/users": "http://localhost:8080",
      "/api/v1/croquettes": "http://localhost:8080",
      "/api/v1/ratings": "http://localhost:8080",
      "/api/v1/comments": "http://localhost:8080",
      "/api/v1/recommendations": "http://localhost:8080",
    },
  },
});
