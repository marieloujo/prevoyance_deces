<?php

namespace App\Repositories\Client\Interfaces;

interface ClientRepositoryInterface
{

    /**
     * Get's all clients.
     *
     * @return mixed
     */
    public function all();

    /**
     * Deletes an client.
     *
     * @param int
     */
    public function delete($id);

    /**
     * Get's an client by it's ID
     *
     * @param int
     */
    public function getById($id);


    /**
     * Create a client.
     *
     * @param array
     */
    public function create(array $client_data);

    /**
     * Update an client.
     *
     * @param int
     * @param array
     */
    public function update($id, array $client_data);


}