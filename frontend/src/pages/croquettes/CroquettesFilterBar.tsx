import styled from "styled-components";
import CroquettesFilterBarSearch from "./CroquettesFilterBarSearch";
import CroquettesFilterBarSortBy from "./CroquettesFilterBarSortBy";
import CroquettesFilterBarFilter from "./CroquettesFilterBarFilter";

const StyledDiv = styled.div`
  display: flex;
  justify-content: space-between;
  border: 1px solid var(--color-grey-300);
  border-radius: var(--border-radius-sm);
  padding: 1.2rem 2.4rem;
`;

const FilterSortDiv = styled.div`
  display: flex;
  gap: 1.2rem;
  align-items: center;
`;

function CroquettesFilterBar() {
  return (
    <StyledDiv>
      <FilterSortDiv>
        <CroquettesFilterBarFilter />
        <CroquettesFilterBarSortBy />
      </FilterSortDiv>

      <CroquettesFilterBarSearch />
    </StyledDiv>
  );
}

export default CroquettesFilterBar;
