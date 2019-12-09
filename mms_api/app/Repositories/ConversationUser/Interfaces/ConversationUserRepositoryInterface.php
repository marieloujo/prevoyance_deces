<?php

namespace App\Repositories\ConversationUser\Interfaces;

interface ConversationUserRepositoryInterface
{
    
    /**
     * Get's all conversationUsers.
     *
     * @return mixed
     */
    public function all();

    /**
     * Deletes an conversationUser.
     *
     * @param int
     */
    public function delete($id);

    /**
     * Get's an conversationUser by it's ID
     *
     * @param int
     */
    public function getById($id);


    /**
     * Create a conversationUser.
     *
     * @param array
     */
    public function create(array $conversationUser_data);

    /**
     * Update an conversationUser.
     *
     * @param int
     * @param array
     */
    public function update($id, array $conversationUser_data);


}