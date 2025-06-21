import { Outlet } from "react-router-dom";
import styled from "styled-components";
import Header from "./Header";
import Footer from "./Footer";

const StyledResourceUI = styled.main`
  display: grid;
  grid-template-rows: 6.4rem 1fr 3.2rem;
  height: 100dvh;
  width: 100vw;
  max-height: 100dvh;

  margin: 0;
  padding: 0;
  /* overflow: hidden; */
`;

const Div = styled.div`
  display: flex;
`;

function AppLayout() {
  return (
    <Div>
      <StyledResourceUI>
        <Header />
        <Outlet />
        <Footer />
      </StyledResourceUI>
    </Div>
  );
}

export default AppLayout;
