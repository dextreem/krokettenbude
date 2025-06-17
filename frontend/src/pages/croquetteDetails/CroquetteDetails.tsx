import { useParams } from "react-router-dom";
import croquettesData from "../../croquette_countries_refined.json";
import type { CroquetteResponse } from "../../api-client";
import Spinner from "../../components/Spinner";
import CroquetteCard from "../../components/croquetteCard/CroquetteCard";
import styled from "styled-components";
import CroquetteRating from "./CroquetteRating";
import CroquetteComments from "./CroquetteComments";

const StyledDiv = styled.div`
  display: grid;
  gap: 2.4rem;
  margin: auto;
  padding-top: 2.4rem;
  padding-bottom: 1.2rem;
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

const croquettes: CroquetteResponse[] = croquettesData;

function CroquetteDetails() {
  const { id } = useParams<{ id: string }>();
  const croquette = croquettes.find((c) => c.id == id);

  if (!croquette) return <Spinner />;

  return (
    <StyledDiv>
      <CardWrapper>
        <CroquetteCard croquetteData={croquette} />
      </CardWrapper>

      <InteractionDiv>
        <CroquetteRating />
        <CroquetteComments croquetteId={croquette.id} />
      </InteractionDiv>
    </StyledDiv>
  );
}

export default CroquetteDetails;
