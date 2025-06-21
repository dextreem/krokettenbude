import { useQuery } from "@tanstack/react-query";
import ThreeColumnCardLayout from "../../components/ThreRowCardLayout";
import type { CroquetteRecommendationResponse } from "../../api-client";
import CroquetteCard from "../../components/croquetteCard/CroquetteCard";
import styled from "styled-components";
import { scoreToPercentage } from "../../utils/textUtils";

const StyledDiv = styled.div`
  display: flex;
  gap: 1.2rem;
`;

const Score = styled.div`
  display: grid;
  gap: 1.2rem;
  margin: auto;
  margin-left: 1.2rem;
`;
function RecommendationsResult() {
  const { data: recommendations = [] } = useQuery<
    CroquetteRecommendationResponse[]
  >({
    queryKey: ["recommendationResults"],
    queryFn: () => Promise.resolve([]),
    enabled: false,
  });

  const croquettes = recommendations.map((r) => {
    return {
      id: r.id ?? 0,
      content: (
        <StyledDiv>
          <Score>Matches {scoreToPercentage(r.score ?? 0)}%</Score>
          <CroquetteCard croquetteData={r} />
        </StyledDiv>
      ),
    };
  });

  return <ThreeColumnCardLayout croquettes={croquettes} />;
}

export default RecommendationsResult;
