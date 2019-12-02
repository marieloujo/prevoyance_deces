<?php

namespace App\Repositories\Document\Interfaces;

interface DocumentRepositoryInterface
{

    /**
     * Get's all assures.
     *
     * @return mixed
     */
    public function all();

    /**
     * Deletes an document.
     *
     * @param int
     */
    public function delete($id);

    /**
     * Get's an document by it's ID
     *
     * @param int
     */
    public function getById($id);


    /**
     * Create a document.
     *
     * @param array
     */
    public function create(array $document_data);

    /**
     * Update an document.
     *
     * @param int
     * @param array
     */
    public function update($id, array $document_data);


}