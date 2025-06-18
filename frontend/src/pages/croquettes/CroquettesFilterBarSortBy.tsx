import { RetrieveAllCroquettesSortByEnum } from "../../api-client";
import Dropdown, { type DropdownOption } from "../../components/DropDown";
import { useCroquetteFiltersStore } from "../../stores/FilterSearchState";

const sortByOptions: DropdownOption[] = Object.entries(
  RetrieveAllCroquettesSortByEnum
).map(
  ([key, value]): DropdownOption => ({
    label: key,
    value: value,
  })
);

function CroquettesFilterBarSortBy() {
  const sortBy = useCroquetteFiltersStore((state) => state.filters.sortBy);
  const setFilters = useCroquetteFiltersStore((state) => state.setFilters);

  const handleOnChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setFilters({
      sortBy: event.target
        .value as (typeof RetrieveAllCroquettesSortByEnum)[keyof typeof RetrieveAllCroquettesSortByEnum],
    });
  };

  return (
    <Dropdown
      options={sortByOptions}
      value={sortBy ?? sortByOptions[0].value}
      onChange={handleOnChange}
      label="Sort:"
    />
  );
}

export default CroquettesFilterBarSortBy;
