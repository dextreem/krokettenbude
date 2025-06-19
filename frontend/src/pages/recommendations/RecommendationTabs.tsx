import TextArea from "../../components/TexxtArea";
import Button from "../../components/Button";
import TabbedWindow from "../../components/TabedWindows";
import styled from "styled-components";
import Input from "../../components/Input";
import {
  CroquetteRecommendationRequestFormEnum,
  type CroquetteLLMRecommendationRequest,
  type CroquetteRecommendationRequest,
} from "../../api-client";
import { useForm } from "react-hook-form";
import {
  useRecommendation,
  useRecommendationByText,
} from "../../hooks/api/useRecommendationApi";
import Spinner from "../../components/Spinner";

const StyledForm = styled.form`
  display: grid;
  gap: 1.2rem;
`;

function RecommendationTabs() {
  const { recommendCroquette, isRecommending } = useRecommendation();
  const { recommendCroquetteByText, isRecommendingByText } =
    useRecommendationByText();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<CroquetteRecommendationRequest>();
  const {
    register: registerLLM,
    handleSubmit: handleSubmitLLM,
    formState: { errors: errorsLLM },
  } = useForm<CroquetteLLMRecommendationRequest>({
    defaultValues: {
      description:
        "I like very hot food. I also like my croquettes soft. I'm not a vegan and I enjoy round food.",
    },
  });

  const isLoading = isRecommending || isRecommendingByText;

  if (isLoading) return <Spinner />;

  const onSubmitRequest = async (data: CroquetteRecommendationRequest) => {
    recommendCroquette({ croquetteRecommendationRequest: data });
  };

  const onSubmitRequestLLM = async (
    data: CroquetteLLMRecommendationRequest
  ) => {
    recommendCroquetteByText({ croquetteLLMRecommendationRequest: data });
  };

  const tabs = [
    {
      label: "By Description",
      content: (
        <form onSubmit={handleSubmitLLM(onSubmitRequestLLM)}>
          <div style={{ display: "grid", gap: "1rem" }}>
            <TextArea
              placeholder="Describe your croquette idea..."
              {...registerLLM("description", { required: true })}
            />
            {errorsLLM.description && (
              <p style={{ color: "red" }}>Description is required.</p>
            )}
            <Button type="submit" disabled={isLoading}>
              {isLoading ? "Submitting..." : "Commit"}
            </Button>
          </div>
        </form>
      ),
    },
    {
      label: "Manually",
      content: (
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
      ),
    },
  ];

  return <TabbedWindow tabs={tabs} />;
}

export default RecommendationTabs;
