import Dropdown, { type DropdownOption } from "./DropDown";

const options: DropdownOption[] = [
  { label: "All", value: "" },
  ...[1, 2, 3, 4, 5].map((n) => ({
    label: `> ${n.toString()}`,
    value: n.toString(),
  })),
];

function Dropdown1to5({
  label,
  value,
  onChange,
}: {
  label: string;
  value?: number;
  onChange: (v: number | undefined) => void;
}) {
  function handleOnChange(e: React.ChangeEvent<HTMLSelectElement>) {
    const val = e.target.value;
    onChange(val === "" ? undefined : Number(val));
  }

  return (
    <Dropdown
      options={options}
      value={value !== undefined ? value.toString() : ""}
      label={label}
      onChange={handleOnChange}
    />
  );
}

export default Dropdown1to5;
