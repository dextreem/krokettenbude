import TextArea from "../../components/TexxtArea";
import Button from "../../components/Button";
import TabbedWindow from "../../components/TabedWindows";
import styled from "styled-components";
import Input from "../../components/Input";
import {
  CroquetteRecommendationRequestFormEnum,
  type CroquetteRecommendationRequest,
} from "../../api-client";
import { useForm } from "react-hook-form";
import { useRecommendation } from "../../hooks/api/useRecommendationApi";
import Spinner from "../../components/Spinner";

const StyledForm = styled.form`
  display: grid;
  gap: 1.2rem;
`;

function RecommendationTabManual() {
  const { recommendCroquette, isRecommending } = useRecommendation();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<CroquetteRecommendationRequest>();

  if (isRecommending) return <Spinner />;

  const onSubmitRequest = async (data: CroquetteRecommendationRequest) => {
    recommendCroquette({ croquetteRecommendationRequest: data });
  };

  return (
    <div>
      <StyledForm onSubmit={handleSubmit(onSubmitRequest)}>
        <div>
          <label>Crunchiness (1-5)</label>
          <Input
            type="number"
            {...register("preferredCrunchiness", {
              required: true,
              min: 1,
              max: 5,
            })}
          />
          {errors.preferredCrunchiness && (
            <span>preferredCrunchiness must be 1-5</span>
          )}
        </div>

        <div>
          <label>Spiciness (1-5)</label>
          <Input
            type="number"
            {...register("preferredSpiciness", {
              required: true,
              min: 1,
              max: 5,
            })}
          />
          {errors.preferredSpiciness && <span>Spiciness must be 1-5</span>}
        </div>

        <div>
          <label>Vegan</label>
          <Input type="checkbox" {...register("vegan")} />
        </div>

        <div>
          <label>Form</label>
          <select {...register("form", { required: true })}>
            {Object.values(CroquetteRecommendationRequestFormEnum).map(
              (formValue) => (
                <option key={formValue} value={formValue}>
                  {formValue}
                </option>
              )
            )}
          </select>
          {errors.form && <span>Form is required</span>}
        </div>

        <button type="submit">Commit</button>
      </StyledForm>
    </div>
  );
}

export default RecommendationTabManual;
