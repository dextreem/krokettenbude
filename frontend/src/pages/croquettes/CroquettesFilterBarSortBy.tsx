import { RetrieveAllCroquettesSortByEnum } from "../../api-client";
import Dropdown, { type DropdownOption } from "../../components/DropDown";

const sortByOptions: DropdownOption[] = Object.entries(
  RetrieveAllCroquettesSortByEnum
).map(
  ([key, value]): DropdownOption => ({
    label: key,
    value: value,
  })
);

function CroquettesFilterBarSortBy() {
  return (
    <Dropdown
      options={sortByOptions}
      value={sortByOptions[0].value}
      onChange={() => alert("Implement this you lazy bastard")} // TODO: Implement
      label="Sort:"
    />
  );
}

export default CroquettesFilterBarSortBy;
