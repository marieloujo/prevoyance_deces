<?php

namespace App\Repositories\Benefice\Interfaces;

interface BeneficeRepositoryInterface
{

    /**
     * Get's all benefices.
     *
     * @return mixed
     */
    public function all();

    /**
     * Deletes an benefice.
     *
     * @param int
     */
    public function delete($id);

    /**
     * Get's an benefice by it's ID
     *
     * @param int
     */
    public function getById($id);


    /**
     * Create a benefice.
     *
     * @param array
     */
    public function create(array $benefice_data);

    /**
     * Update an benefice.
     *
     * @param int
     * @param array
     */
    public function update($id, array $benefice_data);


}