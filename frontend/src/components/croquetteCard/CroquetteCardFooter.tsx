import { HiStar } from "react-icons/hi2";
import styled from "styled-components";
import type { CroquetteResponse } from "../../api-client";
import { getRandomDouble } from "../../utils/textUtils";
import { getCroquetteFormEmoji } from "../../utils/croquetteForms";

const StyledDiv = styled.div`
  display: flex;
  justify-content: space-between;
`;

const Details = styled.div`
  display: flex;
  gap: 2.4rem;
  align-items: center;
`;

const Rating = styled.div`
  display: flex;
  gap: 0.6rem;
  align-items: center;
  margin-left: auto;
`;

const NoRating = styled.span`
  font-size: small;
  font-style: italic;
`;

const crunchEmojis = ["🍟", "🍟🍟", "🍟🍟🍟", "🍟🍟🍟🍟", "🍟🍟🍟🍟🍟"];
const spiceEmojis = ["🌶️", "🌶️🌶️", "🌶️🌶️🌶️", "🌶️🌶️🌶️🌶️", "🌶️🌶️🌶️🌶️🌶️"];

function CroquetteCardFooter({
  croquetteData,
}: {
  croquetteData: CroquetteResponse;
}) {
  return (
    <StyledDiv>
      <Details>
        <span title={`Form: ${croquetteData.form}`}>
          {getCroquetteFormEmoji(croquetteData.form)}
        </span>
        <span title={`Crunchiness: ${croquetteData.crunchiness}`}>
          {crunchEmojis[croquetteData.crunchiness - 1]}
        </span>
        <span title={`Spiciness: ${croquetteData.spiciness}`}>
          {spiceEmojis[croquetteData.spiciness - 1]}
        </span>
        {croquetteData.vegan && (
          <span title="This croquette is vegan!">🌱</span>
        )}
      </Details>
      {croquetteData.averageRating ? (
        <Rating>
          <HiStar />
          {croquetteData.averageRating.toFixed(1)}
        </Rating>
      ) : (
        <NoRating>Not yet rated</NoRating>
      )}
    </StyledDiv>
  );
}

export default CroquetteCardFooter;
