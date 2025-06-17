import styled from "styled-components";
import TextArea from "../../components/TexxtArea";
import Button from "../../components/Button";
import { useState } from "react";
import type { CommentResponse } from "../../api-client";
import CommentList from "./CommentList";

const StyledDiv = styled.div`
  width: 50rem;
  display: grid;
  gap: 1.2rem;
`;

const StyledButton = styled(Button)`
  margin-left: auto;
`;

function CroquetteComments({ croquetteId }: { croquetteId?: number }) {
  const [comment, setComment] = useState<string>("");
  const [comments, setComments] = useState<CommentResponse[]>([]);

  const handleSend = () => {
    const newComment: CommentResponse = {
      comment,
      croquetteId,
      userId: 1,
    };

    setComments((prev) => [...prev, newComment]);
    setComment("");
  };

  return (
    <StyledDiv>
      <TextArea
        onChange={(e) => setComment(e.target.value)}
        placeholder="Write a comment"
        value={comment}
      />
      <StyledButton onClick={handleSend}>Send</StyledButton>
      <CommentList commentList={comments} />
    </StyledDiv>
  );
}

export default CroquetteComments;
