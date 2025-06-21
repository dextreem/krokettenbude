import styled from "styled-components";
import CroquettesFilterBarSearch from "./CroquettesFilterBarSearch";
import CroquettesFilterBarSortBy from "./CroquettesFilterBarSortBy";
import CroquettesFilterBarFilter from "./CroquettesFilterBarFilter";
import { HiTrash } from "react-icons/hi2";
import ButtonIcon from "../../components/ButtonIcon";
import { useCroquetteFiltersStore } from "../../stores/FilterSearchState";
import { RetrieveAllCroquettesSortDirectionEnum } from "../../api-client";
import { HiArrowSmDown, HiArrowSmUp } from "react-icons/hi";

const StyledDiv = styled.div`
  display: flex;
  justify-content: space-between;
`;

const FilterSortDiv = styled.div`
  display: flex;
  gap: 1.2rem;
  align-items: center;
  justify-content: end;
`;

const OuterDiv = styled.div`
  display: grid;
  gap: 1.2rem;

  border: 1px solid var(--color-grey-300);
  border-radius: var(--border-radius-sm);
  padding: 1.2rem 2.4rem;
`;

function CroquettesFilterBar() {
  const resetFilters = useCroquetteFiltersStore((state) => state.resetFilters);
  const ascending = useCroquetteFiltersStore(
    (state) => state.filters.sortDirection
  );
  const setFilters = useCroquetteFiltersStore((state) => state.setFilters);

  return (
    <>
      <OuterDiv>
        <StyledDiv>
          <CroquettesFilterBarFilter />
          <FilterSortDiv>
            <CroquettesFilterBarSearch />
            <ButtonIcon onClick={resetFilters}>
              <HiTrash />
            </ButtonIcon>
          </FilterSortDiv>
        </StyledDiv>
      </OuterDiv>
      <FilterSortDiv>
        <CroquettesFilterBarSortBy />
        <ButtonIcon
          onClick={() =>
            setFilters({
              sortDirection:
                ascending === RetrieveAllCroquettesSortDirectionEnum.Asc
                  ? RetrieveAllCroquettesSortDirectionEnum.Desc
                  : RetrieveAllCroquettesSortDirectionEnum.Asc,
            })
          }
        >
          {ascending === RetrieveAllCroquettesSortDirectionEnum.Asc ? (
            <HiArrowSmUp />
          ) : (
            <HiArrowSmDown />
          )}
        </ButtonIcon>
      </FilterSortDiv>
    </>
  );
}

export default CroquettesFilterBar;
