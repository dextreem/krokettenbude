import { useForm } from "react-hook-form";
import {
  CroquetteRecommendationRequestFormEnum,
  type CroquetteRecommendationRequest,
} from "../../api-client";
import { useRecommendation } from "../../hooks/api/useRecommendationApi";
import Spinner from "../../components/Spinner";
import Input from "../../components/Input";
import styled from "styled-components";

const PageWrapper = styled.div`
  padding: 2rem 1.2rem;
  max-width: 100rem;
`;

const FormWrapper = styled.form`
  display: flex;
  flex-wrap: wrap;
  gap: 2.4rem;
  align-items: flex-end;
  justify-content: center;
  margin: 0 auto;
  max-width: 80rem;
`;

const Field = styled.div`
  display: flex;
  flex-direction: column;
  max-width: 160px;

  label {
    margin-bottom: 0.4rem;
    font-weight: 500;
  }

  span {
    color: red;
    font-size: 0.85rem;
    margin-top: 0.3rem;
  }

  select {
    padding: 0.4rem;
    border: 1px solid #ccc;
    border-radius: 0.3rem;
  }

  input[type="checkbox"] {
    margin-left: 0.6rem;
  }
`;

const CheckboxField = styled(Field)`
  flex-direction: row;
  align-items: center;
  max-width: unset;

  label {
    margin: 0 0.6rem 0 0;
  }
`;

const SubmitButton = styled.button`
  padding: 0.8rem 1.6rem;
  font-size: 1rem;
  font-weight: bold;
  background-color: #222;
  color: white;
  border: none;
  border-radius: 0.4rem;
  cursor: pointer;

  &:hover {
    background-color: #444;
  }
`;

const SpinnerWrapper = styled.div`
  display: flex;
  gap: 1rem;
  align-items: center;
  justify-content: center;
  padding: 4rem;
  font-size: 1.2rem;
`;

export default function RecommendationTabManual() {
  const { recommendCroquette, isRecommending } = useRecommendation();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<CroquetteRecommendationRequest>();

  const onSubmitRequest = async (data: CroquetteRecommendationRequest) => {
    recommendCroquette({ croquetteRecommendationRequest: data });
  };

  if (isRecommending)
    return (
      <SpinnerWrapper>
        <Spinner />
        <span>Recommending...</span>
      </SpinnerWrapper>
    );

  return (
    <PageWrapper>
      <FormWrapper onSubmit={handleSubmit(onSubmitRequest)}>
        <Field>
          <label htmlFor="crunchiness">Crunchiness (1–5)</label>
          <Input
            id="crunchiness"
            type="number"
            {...register("preferredCrunchiness", {
              required: true,
              min: 1,
              max: 5,
            })}
          />
          {errors.preferredCrunchiness && <span>Must be 1–5</span>}
        </Field>

        <Field>
          <label htmlFor="spiciness">Spiciness (1–5)</label>
          <Input
            id="spiciness"
            type="number"
            {...register("preferredSpiciness", {
              required: true,
              min: 1,
              max: 5,
            })}
          />
          {errors.preferredSpiciness && <span>Must be 1–5</span>}
        </Field>

        <CheckboxField>
          <label htmlFor="vegan">Vegan</label>
          <Input id="vegan" type="checkbox" {...register("vegan")} />
        </CheckboxField>

        <Field>
          <label htmlFor="form">Form</label>
          <select id="form" {...register("form", { required: true })}>
            {Object.values(CroquetteRecommendationRequestFormEnum).map(
              (formValue) => (
                <option key={formValue} value={formValue}>
                  {formValue}
                </option>
              )
            )}
          </select>
          {errors.form && <span>Required</span>}
        </Field>

        <SubmitButton type="submit">Commit</SubmitButton>
      </FormWrapper>
    </PageWrapper>
  );
}
