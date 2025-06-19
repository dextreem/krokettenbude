import styled from "styled-components";
import CroquettesFilterBar from "./CroquettesFilterBar";
import CroquettesList from "./CroquettesList";
import { ROUTES } from "../../utils/constants";
import LinkButton from "../../components/LinkButton";
import { HiPlus } from "react-icons/hi2";

const StyledDiv = styled.div`
  margin: 2.4rem auto 0;
  padding-bottom: 1.2rem;
  width: 100rem;

  display: grid;
  grid-template-rows: 10rem 1fr 10rem;
  gap: 2.4rem;
`;

const CenteredWrapper = styled.div`
  justify-self: center;
`;

function Croquettes() {
  return (
    <StyledDiv>
      <CroquettesFilterBar />
      <CroquettesList />
      <CenteredWrapper>
        <LinkButton to={`/${ROUTES.CREATE_CROQUETTE}`}>
          <HiPlus />
        </LinkButton>
      </CenteredWrapper>
    </StyledDiv>
  );
}

export default Croquettes;
