import styled from "styled-components";
import TextArea from "../../components/TexxtArea";
import Button from "../../components/Button";
import { useState } from "react";
import CommentList from "./CommentList";
import { useCreateComment } from "../../hooks/api/useCommentApi";
import { HiPaperAirplane } from "react-icons/hi2";
import useSessionState from "../../stores/SessionState";

const StyledDiv = styled.div`
  display: grid;
  gap: 1.2rem;
`;

const StyledTextArea = styled(TextArea)`
  width: 50rem;
  margin: auto;
`;

const StyledP = styled.div`
  margin: auto;
`;

const StyledButton = styled(Button)`
  align-items: center;
  display: flex;
  gap: 1.2rem;
  margin: auto;
`;

function CroquetteComments({ croquetteId }: { croquetteId: number }) {
  const userDetails = useSessionState((state) => state.userDetails);
  const loggedIn = userDetails ? true : false;
  const { createComment } = useCreateComment();
  const [comment, setComment] = useState<string>("");

  const handleSend = () => {
    createComment({ commentCreateRequest: { croquetteId, comment } });
    setComment("");
  };

  return (
    <StyledDiv>
      {loggedIn && (
        <>
          <StyledTextArea
            onChange={(e) => setComment(e.target.value)}
            placeholder="Write a comment"
            value={comment}
            disabled={!loggedIn}
          />
          <StyledButton onClick={handleSend} disabled={!loggedIn}>
            <HiPaperAirplane />
            <span>Send</span>
          </StyledButton>
        </>
      )}
      <CommentList croquetteId={croquetteId} />
    </StyledDiv>
  );
}

export default CroquetteComments;
