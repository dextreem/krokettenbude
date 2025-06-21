import styled from "styled-components";
import Input from "../../components/Input";
import { useCroquetteFiltersStore } from "../../stores/FilterSearchState";

const StyledInput = styled(Input)`
  width: 25rem;
  transition: width 0.3s ease;

  &:focus,
  &:hover {
    border-color: var(--color-brand-200);
    width: 35rem;
  }
`;

function CroquettesFilterBarSearch() {
  const { descriptionContains } = useCroquetteFiltersStore(
    (state) => state.filters
  );
  const setFilter = useCroquetteFiltersStore((state) => state.setFilters);

  const handleOnChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newSearchValue = event.target.value;
    setFilter({
      descriptionContains: newSearchValue,
      nameContains: newSearchValue,
      country: newSearchValue,
    });
  };

  return (
    <StyledInput
      value={descriptionContains ?? ""}
      onChange={handleOnChange}
      type="text"
      id="search"
      placeholder="Search"
    />
  );
}

export default CroquettesFilterBarSearch;
