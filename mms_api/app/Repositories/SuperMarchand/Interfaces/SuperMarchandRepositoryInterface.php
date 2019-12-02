<?php

namespace App\Repositories\SuperMarchand\Interfaces;

interface SuperMarchandRepositoryInterface
{

    /**
     * Get's all supermarchands.
     *
     * @return mixed
     */
    public function all();

    /**
     * Deletes an supermarchand.
     *
     * @param int
     */
    public function delete($id);

    /**
     * Get's an supermarchand by it's ID
     *
     * @param int
     */
    public function getById($id);

    /**
     * Create a supermarchand.
     *
     * @param array
     */
    public function create(array $supermarchand_data);

    /**
     * Update an supermarchand.
     *
     * @param int
     * @param array
     */
    public function update($id, array $supermarchand_data);


}