import React from "react";
import styled from "styled-components";

const DropdownContainerOuterWrapper = styled.div`
  display: flex;
  flex-direction: row-reverse;
  margin-top: 0;
`;

const DropdownContainerWrapper = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
`;

const DropdownLabel = styled.label`
  margin-right: 10px;
`;

const DropdownContainer = styled.select`
  border: 1px solid var(--color-grey-700);
  background-color: var(--color-grey-fixed-white);
  color: var(--color-grey-fixed-dark);
  border-radius: var(--border-radius-sm);
  padding: 0.8rem 1.2rem;
  box-shadow: var(--shadow-sm);
  width: 100%;

  /* option {
    color: black;
    text-align: center;
  } */
`;

// Props type
export interface DropdownOption {
  label: string;
  value: string;
}

interface DropdownProps {
  options: DropdownOption[];
  value: string;
  onChange: (event: React.ChangeEvent<HTMLSelectElement>) => void;
  label?: string;
}

// Functional component
const Dropdown: React.FC<DropdownProps> = ({
  options,
  value,
  onChange,
  label,
}) => {
  const handleOptionClick = (e: React.MouseEvent<HTMLSelectElement>) => {
    e.stopPropagation();
  };

  return (
    <DropdownContainerOuterWrapper>
      <DropdownContainerWrapper>
        {label && <DropdownLabel>{label}</DropdownLabel>}
        <DropdownContainer
          value={value}
          onChange={onChange}
          onClick={handleOptionClick}
        >
          {options.map((option) => (
            <option key={option.value} value={option.value}>
              {option.label}
            </option>
          ))}
        </DropdownContainer>
      </DropdownContainerWrapper>
    </DropdownContainerOuterWrapper>
  );
};

export default Dropdown;
