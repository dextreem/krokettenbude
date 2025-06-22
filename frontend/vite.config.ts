import { defineConfig, loadEnv } from "vite";
import react from "@vitejs/plugin-react-swc";

export default defineConfig(({ command, mode }) => {
  const env = loadEnv(mode, process.cwd(), "");

  console.log("Configured API BASE URL:", env.VITE_API_BASE_URL);

  return {
    plugins: [react()],
    server:
      command === "serve"
        ? {
            proxy: {
              "/api/v1/users": env.VITE_API_BASE_URL || "http://localhost:8080",
              "/api/v1/croquettes":
                env.VITE_API_BASE_URL || "http://localhost:8080",
              "/api/v1/ratings":
                env.VITE_API_BASE_URL || "http://localhost:8080",
              "/api/v1/comments":
                env.VITE_API_BASE_URL || "http://localhost:8080",
              "/api/v1/recommendations":
                env.VITE_API_BASE_URL || "http://localhost:8080",
            },
          }
        : undefined,
  };
});
