import styled from "styled-components";
import RecommendationTabs from "./RecommendationTabs";
import RecommendationsResult from "./RecommendationsResult";
import Heading from "../../components/Heading";

const StyledDiv = styled.div`
  margin: 2.4rem auto 0;
  padding-bottom: 1.2rem;
  width: 100rem;

  display: flex;
  flex-direction: column;
  gap: 2.4rem;
`;

function Recommendations() {
  return (
    <StyledDiv>
      <Heading as="h2">Recommend Croquettes</Heading>
      <RecommendationTabs />
      <RecommendationsResult />
    </StyledDiv>
  );
}

export default Recommendations;
