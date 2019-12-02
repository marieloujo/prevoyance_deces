<?php

namespace App\Repositories\Assurer\Interfaces;

interface AssurerRepositoryInterface
{

    /**
     * Get's all assures.
     *
     * @return mixed
     */
    public function all();

    /**
     * Deletes an assurer.
     *
     * @param int
     */
    public function delete($id);

    /**
     * Get's an assurer by it's ID
     *
     * @param int
     */
    public function getById($id);


    /**
     * Create a assurer.
     *
     * @param array
     */
    public function create(array $assurer_data);

    /**
     * Update an assurer.
     *
     * @param int
     * @param array
     */
    public function update($id, array $assurer_data);


}