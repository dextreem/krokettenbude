import { useNavigate, useParams } from "react-router-dom";
import Spinner from "../../components/Spinner";
import CroquetteCard from "../../components/croquetteCard/CroquetteCard";
import styled from "styled-components";
import CroquetteRating from "./CroquetteRating";
import CroquetteComments from "./CroquetteComments";
import { useGetSingleCroquette } from "../../hooks/api/useCroquetteApi";
import { ROUTES } from "../../utils/constants";

const StyledDiv = styled.div`
  margin: auto;
  display: flex;
  flex-direction: column;
  gap: 2.4rem;
  max-width: 100rem;
`;

const CardWrapper = styled.div`
  display: flex;
  gap: 1.2rem;
`;

const InteractionDiv = styled.div`
  margin: auto;
  display: grid;
  gap: 4.8rem;
`;

function CroquetteDetails() {
  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();
  const parsed = Number(id);
  if (isNaN(parsed)) navigate(`/${ROUTES.CROQUETTES}`);

  const { croquette, isCroquetteLoading } = useGetSingleCroquette(parsed);

  if (isCroquetteLoading) return <Spinner />;
  if (!croquette) return <div>Croquette not found :( </div>;

  return (
    <StyledDiv>
      <CardWrapper>
        <CroquetteCard croquetteData={croquette} />
      </CardWrapper>

      <InteractionDiv>
        <CroquetteRating croquetteId={parsed} />
        <CroquetteComments croquetteId={parsed} />
      </InteractionDiv>
    </StyledDiv>
  );
}

export default CroquetteDetails;
