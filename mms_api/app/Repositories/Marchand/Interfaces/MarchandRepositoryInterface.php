<?php

namespace App\Repositories\Marchand\Interfaces;

interface MarchandRepositoryInterface
{

    /**
     * Get's all marchands.
     *
     * @return mixed
     */
    public function all();

    /**
     * Deletes an marchand.
     *
     * @param int
     */
    public function delete($id);

    /**
     * Get's an marchand by it's ID
     *
     * @param int
     */
    public function getById($id);

    /**
     * Create a marchand.
     *
     * @param array
     */
    public function create(array $marchand_data);

    /**
     * Update an marchand.
     *
     * @param int
     * @param array
     */
    public function update($id, array $marchand_data);

    /**
     * list of marchand clients.
     *
     * @param int
     */
    public function getClients($id);
    public function getContrats($marchand,$client);


}