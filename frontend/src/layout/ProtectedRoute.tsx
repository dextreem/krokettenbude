import { useNavigate } from "react-router-dom";
import Spinner from "../components/Spinner";
import styled from "styled-components";
import { useEffect, type JSX, type ReactNode } from "react";
import useSessionState from "../stores/sessionState";

const FullPage = styled.div`
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
`;

interface ProtectedRouteProps {
  children: ReactNode;
}

function ProtectedRoute({ children }: ProtectedRouteProps): JSX.Element {
  const navigate = useNavigate();
  const token = useSessionState((state) => state.token);

  const isLoggedIn = !!token;

  useEffect(() => {
    if (!isLoggedIn) {
      navigate(`/login`); // TODO: use constant
    }
  }, [isLoggedIn, navigate]);

  if (isLoggedIn) {
    return <>{children}</>;
  }

  return (
    <FullPage>
      <Spinner />
    </FullPage>
  );
}

export default ProtectedRoute;
