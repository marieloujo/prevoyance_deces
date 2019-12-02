<?php

namespace App\Repositories\Direction\Interfaces;

interface DirectionRepositoryInterface
{

    /**
     * Get's all directions.
     *
     * @return mixed
     */
    public function all();

    /**
     * Deletes an direction.
     *
     * @param int
     */
    public function delete($id);

    /**
     * Get's an direction by it's ID
     *
     * @param int
     */
    public function getById($id);


    /**
     * Create a direction.
     *
     * @param array
     */
    public function create(array $direction_data);

    /**
     * Update an direction.
     *
     * @param int
     * @param array
     */
    public function update($id, array $direction_data);


}