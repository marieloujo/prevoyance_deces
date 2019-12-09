<?php

namespace App\Repositories\Conversation;

use App\Models\Conversation;
use App\Repositories\Conversation\Interfaces\ConversationRepositoryInterface;

class ConversationRepository implements ConversationRepositoryInterface
{
    protected $conversation;

    public function __construct(Conversation $conversation)
    {
        $this->conversation = $conversation;
    }
    /**
     * Get a conversation by it's ID
     *
     * @param int
     * @return collection
     */

    public function getById($id)
    {
        return $this->conversation->findOrfail($id);
    }

    /**
     * Get a conversation by it's ID
     *
     * @param string
     * @return collection
     */

    /**
     * Get's all assurés.
     *
     * @return mixed
     */
    public function all()
    {
        return $this->conversation->paginate();
    }

    /**
     * Delete a conversation.
     *
     * @param int
     */
    public function delete($id)
    {
        $this->conversation->findOrfail($id)->delete();
        
    }

    /**
     * Register a conversation.
     *
     * @param array
     */
    public function create()
    {
            $conversation = new conversation();
            $conversation->save();

            return $conversation;
    }

    /**
     * Update a conversation.
     *
     * @param int
     * @param array
     */
    public function update($id, array $conversation_data)
    {
            $conversation =  $this->conversation->findOrfail($id);
            $conversation->update();
    }

}

