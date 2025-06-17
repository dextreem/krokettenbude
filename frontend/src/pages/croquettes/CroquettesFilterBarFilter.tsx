import Dropdown, { type DropdownOption } from "../../components/DropDown";

const filterOptions: DropdownOption[] = [
  { label: "Implement", value: "Implement" },
  { label: "This!", value: "This!" },
];

function CroquettesFilterBarFilter() {
  return (
    <Dropdown
      options={filterOptions}
      value={filterOptions[0].value}
      onChange={() => alert("Implement this you lazy bastard")} // TODO: Implement
      label="Filter:"
    />
  );
}

export default CroquettesFilterBarFilter;
