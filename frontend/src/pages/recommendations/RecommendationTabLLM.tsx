import TextArea from "../../components/TexxtArea";
import Button from "../../components/Button";
import { type CroquetteLLMRecommendationRequest } from "../../api-client";
import { useForm } from "react-hook-form";
import { useRecommendationByText } from "../../hooks/api/useRecommendationApi";
import Spinner from "../../components/Spinner";

function RecommendationTabLLM() {
  const { recommendCroquetteByText, isRecommendingByText } =
    useRecommendationByText();
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

  const isLoading = isRecommendingByText;

  if (isLoading) return <Spinner />;

  const onSubmitRequestLLM = async (
    data: CroquetteLLMRecommendationRequest
  ) => {
    recommendCroquetteByText({ croquetteLLMRecommendationRequest: data });
  };

  return (
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
  );
}

export default RecommendationTabLLM;
