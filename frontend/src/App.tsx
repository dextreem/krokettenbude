import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import GlobalStyles from "./styles/GlobalStyles";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import AppLayout from "./layout/AppLayout";
import { useEffect } from "react";
import useDarkModeState from "./stores/DarkmodeState";
import { ROUTES } from "./utils/constants";
import Croquettes from "./pages/croquettes/Croquettes";
import ErrorPage from "./pages/error/ErrorPage";
import CroquetteDetails from "./pages/croquetteDetails/CroquetteDetails";
import LoginSignup from "./pages/loginSignup/LoginSignup";
import { Toaster } from "react-hot-toast";
import CreateCroquette from "./pages/croquetteCreate/CreateCroquette";
import ProtectedRoute from "./layout/ProtectedRoute";
import Recommendations from "./pages/recommendations/Recommendations";

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
            <Route
              element={
                <ProtectedRoute>
                  <AppLayout />
                </ProtectedRoute>
              }
            >
              <Route
                path={ROUTES.CREATE_CROQUETTE}
                element={<CreateCroquette />}
              />
            </Route>
            <Route element={<AppLayout />}>
              <Route
                index
                element={<Navigate replace to={ROUTES.CROQUETTES} />}
              />
              <Route path={ROUTES.CROQUETTES} element={<Croquettes />} />

              <Route
                path={`${ROUTES.CROQUETTES}/:id`}
                element={<CroquetteDetails />}
              />
              <Route
                path={ROUTES.LOGIN}
                element={<LoginSignup mode="login" />}
              />
              <Route
                path={ROUTES.SIGNUP}
                element={<LoginSignup mode="signup" />}
              />
              <Route
                path={ROUTES.RECOMMENDATIONS}
                element={<Recommendations />}
              />
            </Route>
            {/* Fallback for every other page */}
            <Route path="*" element={<ErrorPage />} />
          </Routes>
        </BrowserRouter>
      </QueryClientProvider>
      <Toaster
        position="top-center"
        gutter={12}
        containerStyle={{ margin: "8px" }}
        toastOptions={{
          success: {
            duration: 3000,
          },
          error: {
            duration: 15000,
          },
          style: {
            fontSize: "16px",
            maxWidth: "500px",
            padding: "16px 24px",
            backgroundColor: "var(--color-grey-300)",
            color: "var(--color-grey-800)",
          },
        }}
      />
    </>
  );
}

export default App;
