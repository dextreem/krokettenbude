import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import GlobalStyles from "./styles/GlobalStyles";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import ProtectedRoute from "./layout/ProtectedRoute";
import AppLayout from "./layout/AppLayout";
import { useEffect } from "react";
import useDarkModeState from "./stores/DarkmodeState";
import { ROUTES } from "./utils/constants";
import Croquettes from "./pages/croquettes/Croquettes";
import ErrorPage from "./pages/error/ErrorPage";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: 0,
    },
  },
});

function App() {
  const { isDarkMode } = useDarkModeState();

  useEffect(() => {
    // Apply dark mode class to the body based on the state
    if (isDarkMode) {
      document.documentElement.classList.add("dark-mode");
      document.documentElement.classList.remove("light-mode");
    } else {
      document.documentElement.classList.add("light-mode");
      document.documentElement.classList.remove("dark-mode");
    }
  }, [isDarkMode]);

  return (
    <>
      <QueryClientProvider client={queryClient}>
        <ReactQueryDevtools initialIsOpen={false} />
        <GlobalStyles />
        <BrowserRouter>
          <Routes>
            {/* Protected Routes (User page) */}
            <Route
              element={
                <ProtectedRoute>
                  <AppLayout />
                </ProtectedRoute>
              }
            >
              <Route index element={<Navigate replace to={ROUTES.HOME} />} />
            </Route>
            {/* Unprotected Routes (Everything except user page) */}
            <Route element={<AppLayout />}>
              <Route path={ROUTES.HOME} element={<Croquettes />} />
            </Route>
            {/* Fallback for every other page */}
            <Route path="*" element={<ErrorPage />} />
          </Routes>
        </BrowserRouter>
      </QueryClientProvider>
    </>
  );
}

export default App;
