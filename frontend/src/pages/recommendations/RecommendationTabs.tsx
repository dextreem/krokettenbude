import TabbedWindow from "../../components/TabedWindows";
import {
  useRecommendation,
  useRecommendationByText,
} from "../../hooks/api/useRecommendationApi";
import Spinner from "../../components/Spinner";
import RecommendationTabLLM from "./RecommendationTabLLM";
import RecommendationTabManual from "./RecommendationTabManual";

function RecommendationTabs() {
  const { isRecommending } = useRecommendation();
  const { isRecommendingByText } = useRecommendationByText();

  const isLoading = isRecommending || isRecommendingByText;

  if (isLoading) return <Spinner />;

  const tabs = [
    {
      label: "Descriptive",
      content: <RecommendationTabLLM />,
    },
    {
      label: "Options",
      content: <RecommendationTabManual />,
    },
  ];

  return <TabbedWindow tabs={tabs} />;
}

export default RecommendationTabs;
