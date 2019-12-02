<?php

namespace App\Repositories\Document;

use App\Models\Document;
use App\Repositories\Document\Interfaces\DocumentRepositoryInterface;

class DocumentRepository implements DocumentRepositoryInterface
{
    protected $document;

    public function __construct(Document $document)
    {
        $this->document = $document;
    }
    /**
     * Get a assure by it's ID
     *
     * @param int
     * @return collection
     */

    public function getById($id)
    {
        return $this->document->findOrfail($id);
    }

    /**
     * Get a assure by it's ID
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
        return $this->document->paginate();
    }

    /**
     * Delete a assure.
     *
     * @param int
     */
    public function delete($id)
    {
        $this->document->findOrfail($id)->delete();
        
    }

    /**
     * Register a document.
     *
     * @param array
     */
    public function create(array $document_data)
    {
            $document = new Document();
            $document->url = $document_data['url'];
            $document->documenteable_id = $document_data['documenteable_id'];
            $document->documenteable_type = $document_data['documenteable_type'];
            $document->save();

            return $document;
    }

    /**
     * Update a document.
     *
     * @param int
     * @param array
     */
    public function update($id, array $document_data)
    {
            $document =  $this->document->findOrfail($id);
            $document->url = $document_data['url'];
            $document->documenteable_id = $document_data['documenteable_id'];
            $document->documenteable_type = $document_data['documenteable_type'];
            $document->update();
    }

}

