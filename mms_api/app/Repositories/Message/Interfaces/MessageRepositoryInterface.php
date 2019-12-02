<?php

namespace App\Repositories\Message\Interfaces;

interface MessageRepositoryInterface
{

    /**
     * Get's all messages.
     *
     * @return mixed
     */
    public function all();

    /**
     * Deletes an message.
     *
     * @param int
     */
    public function delete($id);

    /**
     * Get's an message by it's ID
     *
     * @param int
     */
    public function getById($id);

    public function getAuthMessage($id);

    /**
     * Create a message.
     *
     * @param array
     */
    public function create(array $message_data);

    /**
     * Update an message.
     *
     * @param int
     * @param array
     */
    public function update($id, array $message_data);


}