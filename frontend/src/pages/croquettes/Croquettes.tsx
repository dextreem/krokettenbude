import styled from "styled-components";
import CroquettesFilterBar from "./CroquettesFilterBar";
import CroquettesList from "./CroquettesList";

const StyledDiv = styled.div`
  margin: auto;
  padding-top: 2.4rem;
  padding-bottom: 1.2rem;
  max-width: 100rem;
`;

function Croquettes() {
  return (
    <StyledDiv>
      <CroquettesFilterBar />
      <CroquettesList />
    </StyledDiv>
  );
}

export default Croquettes;
