import styled from "styled-components";
import CroquettesFilterBar from "./CroquettesFilterBar";
import CroquettesList from "./CroquettesList";
import { ROUTES } from "../../utils/constants";
import LinkButton from "../../components/LinkButton";

const StyledDiv = styled.div`
  margin: auto;
  padding-top: 2.4rem;
  padding-bottom: 1.2rem;
  max-width: 100rem;

  display: grid;
  gap: 2.4rem;
`;

function Croquettes() {
  return (
    <StyledDiv>
      <CroquettesFilterBar />
      <CroquettesList />
      <LinkButton to={`/${ROUTES.CREATE_CROQUETTE}`}>
        Create new Croquette
      </LinkButton>
    </StyledDiv>
  );
}

export default Croquettes;
