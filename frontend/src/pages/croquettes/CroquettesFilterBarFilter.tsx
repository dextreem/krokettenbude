import styled from "styled-components";
import Dropdown1to5 from "../../components/Dropdown1To5";
import Dropdown, { type DropdownOption } from "../../components/DropDown";
import {
  CroquetteRecommendationRequestFormEnum,
  RetrieveAllCroquettesFormEnum,
} from "../../api-client";
import ButtonIcon from "../../components/ButtonIcon";
import { HiFilter, HiOutlineFilter } from "react-icons/hi";
import { useCroquetteFiltersStore } from "../../stores/FilterSearchState";

const StyledDiv = styled.div`
  display: flex;
  gap: 2.4rem;
  justify-content: baseline;
`;

const formOptions: DropdownOption[] = [
  { label: "All", value: "" },
  ...Object.entries(CroquetteRecommendationRequestFormEnum).map(([k, v]) => ({
    label: k,
    value: v,
  })),
];

const veganOptions: DropdownOption[] = [
  { label: "All", value: "" },
  { label: "Yes", value: "true" },
  { label: "No", value: "false" },
];

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
              label="🍟"
              value={filters.crunchiness?.[0]}
              onChange={(selectedValue?: number) => {
                if (selectedValue === undefined) {
                  setFilters({ crunchiness: undefined });
                  return;
                }
                const newCrunchiness = Array.from(
                  { length: 5 - selectedValue + 1 },
                  (_, i) => selectedValue + i
                );
                setFilters({ crunchiness: newCrunchiness });
              }}
            />
            <Dropdown1to5
              label="🌶️"
              value={filters.spiciness?.[0]}
              onChange={(selectedValue?: number) => {
                if (selectedValue === undefined) {
                  setFilters({ spiciness: undefined });
                  return;
                }
                const newSpiciness = Array.from(
                  { length: 5 - selectedValue + 1 },
                  (_, i) => selectedValue + i
                );
                setFilters({ spiciness: newSpiciness });
              }}
            />

            <Dropdown
              label="🌱"
              options={veganOptions}
              value={
                filters.vegan === undefined ? "" : filters.vegan.toString()
              }
              onChange={(e: React.ChangeEvent<HTMLSelectElement>) => {
                const value = e.target.value;
                setFilters({
                  vegan:
                    value === "" ? undefined : value === "true" ? true : false,
                });
              }}
            />

            <Dropdown
              label="Form"
              options={formOptions}
              value={filters.form ?? ""}
              onChange={(e: React.ChangeEvent<HTMLSelectElement>) =>
                setFilters({
                  form:
                    e.target.value === ""
                      ? undefined
                      : (e.target.value as RetrieveAllCroquettesFormEnum),
                })
              }
            />
          </StyledDiv>
        </>
      )}
    </StyledDiv>
  );
}

export default CroquettesFilterBarFilter;
