import Dropdown, { type DropdownOption } from "./DropDown";

const options: DropdownOption[] = [1, 2, 3, 4, 5].map((n) => {
  return { label: n.toString(), value: n.toString() };
});

function Dropdown1to5({
  label,
  value,
  onChange,
}: {
  label: string;
  value: number;
  onChange: (v: number) => void;
}) {
  function handleOnChange(e: React.ChangeEvent<HTMLSelectElement>) {
    onChange(Number(e.target.value));
  }

  return (
    <Dropdown
      options={options}
      value={value.toString()}
      label={label}
      onChange={handleOnChange}
    />
  );
}

export default Dropdown1to5;
