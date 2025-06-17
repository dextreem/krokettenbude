import styled from "styled-components";
import type { CroquetteResponse } from "../../api-client";
import Heading from "../Heading";
import { getFlag } from "../../utils/countryFlags";
import { limitToWords } from "../../utils/textUtils";
import CroquetteCardFooter from "./CroquetteCardFooter";

const StlyedDiv = styled.div`
  display: grid;
  gap: 1.2rem;

  margin: 1.2rem 0;
  margin-right: 1.2rem;
  flex: 1;
`;

const DetailsHeader = styled.div`
  width: 100%;
  display: flex;
  /* justify-content: space-between; */
  align-items: center;
`;

function CroquetteCardDetails({
  croquetteData,
  limitDescription = false,
}: {
  croquetteData: CroquetteResponse;
  limitDescription?: boolean;
}) {
  const description = limitDescription
    ? limitToWords(croquetteData.description, 30)
    : croquetteData.description;

  return (
    <StlyedDiv>
      <DetailsHeader>
        <Heading as="h2">{croquetteData.name}</Heading>
        &nbsp;
        <span>{getFlag(croquetteData.country)}</span>
      </DetailsHeader>

      <p>{description}</p>

      <CroquetteCardFooter croquetteData={croquetteData} />
    </StlyedDiv>
  );
}

export default CroquetteCardDetails;
