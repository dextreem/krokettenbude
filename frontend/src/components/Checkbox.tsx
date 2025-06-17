import styled from "styled-components";
import React from "react";

const StyledCheckBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.8rem;
  font-size: 1.4rem;
  font-weight: 500;

  & input[type="checkbox"] {
    height: 2rem;
    width: 2rem;
    outline-offset: 2px;
    transform-origin: 0;
  }

  & label {
    flex: 1;
    display: flex;
    align-items: center;
    gap: 0.8rem;
  }
`;

interface CheckBoxProps {
  id: string;
  label: string;
  checked: boolean;
  disabled?: boolean;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const CheckBox: React.FC<CheckBoxProps> = ({
  id,
  label,
  checked,
  disabled = false,
  onChange = (e) => console.log("checkbox clicked"),
}) => {
  return (
    <StyledCheckBox>
      <input
        type="checkbox"
        id={id}
        checked={checked}
        disabled={disabled}
        onChange={onChange}
      />
      <label htmlFor={!disabled ? id : undefined}>{label}</label>
    </StyledCheckBox>
  );
};

export default CheckBox;
