import styled from "styled-components";
import Dropdown1to5 from "../../components/Dropdown1To5";
import CheckBox from "../../components/Checkbox";
import Dropdown, { type DropdownOption } from "../../components/DropDown";
import { CroquetteRecommendationRequestFormEnum } from "../../api-client";
import ButtonIcon from "../../components/ButtonIcon";
import { HiFilter, HiOutlineFilter } from "react-icons/hi";
import { useCroquetteFiltersStore } from "../../stores/FilterSearchState";

const StyledDiv = styled.div`
  display: flex;
  gap: 2.4rem;
  justify-content: baseline;
`;

const formOptions: DropdownOption[] = Object.entries(
  CroquetteRecommendationRequestFormEnum
).map(([k, v]) => {
  return {
    label: k,
    value: v,
  };
});

function CroquettesFilterBarFilter() {
  const { filters, setFilters, useFilters, toggleUseFilters } =
    useCroquetteFiltersStore();

  return (
    <StyledDiv>
      <ButtonIcon onClick={toggleUseFilters}>
        {useFilters ? <HiFilter /> : <HiOutlineFilter />}
      </ButtonIcon>

      {useFilters && (
        <>
          <StyledDiv>
            <Dropdown1to5
              label="🌶️"
              value={Math.min(...(filters.spiciness ?? [1]))}
              onChange={(selectedValue: number) => {
                const newSpiciness = Array.from(
                  { length: 5 - selectedValue + 1 },
                  (_, i) => selectedValue + i
                );
                setFilters({ spiciness: newSpiciness });
              }}
            />
            <Dropdown1to5
              label="🍟"
              value={Math.min(...(filters.crunchiness ?? [1]))}
              onChange={(selectedValue: number) => {
                const newSpiciness = Array.from(
                  { length: 5 - selectedValue + 1 },
                  (_, i) => selectedValue + i
                );
                setFilters({ spiciness: newSpiciness });
              }}
            />

            <CheckBox
              id="veganFilter"
              checked={filters.vegan ?? false}
              onChange={(e) => setFilters({ vegan: e.target.checked })}
              label="🌱"
            />

            <Dropdown
              label="Form"
              options={formOptions}
              value={filters.form ?? formOptions[0].value}
              onChange={() => console.log("Changed")}
            />
          </StyledDiv>
        </>
      )}
    </StyledDiv>
  );
}

export default CroquettesFilterBarFilter;
